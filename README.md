# HAP Scala
HomeKit Accessory Protocol implementation in Scala

Although this is still _a work in progress_, the implementation
really works and it's proven to be working 24/7 in a real household.

This project is heavily inspired by
[Beowulfe's HAP-Java](https://github.com/beowulfe/HAP-Java)
which is currently still used as a "server driver".

## Real Scala
The intention was to provide a framework which is enough "Scala-idiomatic".
This especially means no stateful code (there is not a single `var` or `AtomicXxx`),
no mutable collections,
no dependencies on being instance of something.
The framework also heavily uses Traits where possible to enable mixing stuff together.

## Modules
The framework is done in modular way, so you can pick whatever you need.
This currently includes:
- `hap-api`, the main API including the definition of accessories,
their services and characteristics.
- `hap-server-beowulfe`, currently the only real server implementation.
It converts the HAP Scala model into HAP-Java accessories and publishes
them using the internal Netty implementation.
_An alternative implementation purely in Scala is in progress._
- `hap-monix` and `hap-reactivex` are small toolkit modules
enabling to use Observables as a natural implementation of the accessories' states.
- `hap-examples` contains _(you guessed it)_ some examples how to define and run accessories.

## Example
Defining accessories is quite straightforward, see it below or in the `hap-examples` module.

```scala
// assume hap-monix & hap-server-beowulfe are dependencies

// It is usually handy to define a common accessory which will cover the most typical scenarios.
trait BaseAccessory extends SingleServiceAccessory with IdentifyByPrintingLabel {
  this: AccessoryService =>

  protected implicit val scheduler: Scheduler = ??? // or define implicit scheduler per each accessory

  override def manufacturer = "Here comes your company/name"
  override def model = "Name of the model"
  override def serialNumber = "serial"
}

// This is really all you need to do to define a switch!
case class ExampleSwitch(id: Int, label: String)
                        (subject: BehaviorSubject[Boolean])
        extends BaseAccessory with SwitchService {

  override val powerState: PowerStateCharacteristic =
    new ObservableWritableCharacteristic(subject) with PowerStateCharacteristic
}

// Let's run the server using Beowulfe's implementation
object Runner extends App {
  implicit val exec: ExecutionContext = ???

  val switchStream = BehaviorSubject(false)

  val accessories: Seq[HomeKitAccessory] = Seq(
    ExampleSwitch(1001, "An example switch")(switchStream),
  )

  // --- the server definition follows ---

  val server = new HomekitServer(1234)
  val authInfo: HomekitAuthInfo = ??? // here comes the authentication information
  val bridge = server.createBridge(authInfo, "Example Bridge", "You", "BridgeV1", "abcd")

  import beowulfe.BeowulfeAccessoryAdapter.Implicit._ // import implicit accessory converter
  accessories foreach (acc => bridge.addAccessory(acc))

  bridge.start()
}
```

## Limitations
There are some limitations regarding the state of the implementation
or some implications from the underlying implementation.

### Incompleteness
The current intention wasn't to provide complete implementation
of all Apple-defined services and characteristics.
It only defines the ones which I've ever tried to use practically.
On the other hand, the framework doesn't prevent you from adding
any currently unsupported service/characteristic.

Contributions are definitely very welcome!
The completeness would be great, it just wasn't the priority.

### Beowulfe HAP-Java Implications
The underlying server driver has few implications which are necessary to consider.

#### Synchronous supplyValue
Due to the design of `Characteristic.supplyValue`,
which is synchronous (unlike `Characteristic.toJson`),
it is impossible to request states of multiple accessories/characteristics at once.
The framework asks one by one because the execution of `supplyValue` blocks until
the characteristic provides its value, which is then glued to the provided JSON builder.

**The practical impact** is that this complicates getting values which are derived
from a shared data source among multiple characteristics.
Following scenario is impossible to implement:
1. Given 3 accessories internally depending on 1 source of data.
1. Debounce the requests for getting values of all 3 accessories, thus there is 1 request for data.
1. As soon as the data arrives, respond to all 3 accessories with their derived values.

With the synchronous nature, the only possibility is to cache the result of first accessory
and reuse it for the second and third ones.

#### Characteristic does not notify the new value
The value-changed notification is implemented in Beowulfe's framework literally as a notification
that "something changed".
What the framework does, is that it calls `supplyValue` immediately after that notification.
There is unfortunately no way how to distinguish the context in which `supplyValue` was called,
so HAP Scala solves this by short 1-second caching of the notified value.
In other words, the notifications in HAP Scala contain the new value.
To be able to take advantage from it, on each notification, the value is stored to a cache
from where the `supplyValue` tries to pick it up first.
Only if it is not found in that short-term cache, the characteristic is instructed to provide a fresh value.

The **impact** is, that any request for a "fresh" value coming in a 1 second window after a value-change notification
is effectively not performed and it's provided with that cached value.

_Both issues above are described in detail in the code of the `BeowulfeAccessoryAdapter`._

#### IIDs are automatically generated
Unlike AIDs (accessory IDs) which are necessary to provide by each accessory,
the framework automatically generates the IIDs (instance IDs) of the services and characteristics.

This means the IIDs are dependent on the order of services/characteristics and also on their amount.
This can cause some troubles because the specification requires
every service and characteristic to keep its IID for the whole lifetime of the accessory
and IIDs of those which stop existing mustn't be recycled.
The doc even explicitly mentions that it has to keep the IID even after a firmware update.
