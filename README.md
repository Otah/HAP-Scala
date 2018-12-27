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
no dependencies on being instance of something,
the framework also heavily uses `traits` where possible to enable mixing stuff together.

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
trait BaseAccessory extends HomeKitAccessory with SingleServiceAccessory with AccessoryService with IdentifyByPrintingLabel {

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
TBD

- Synchronous state determination
