package com.github.otah.hap.api.internal

/** Traits defining the standard names of the units.
  * You can easily mix them into respective characteristics.
  */
object Units {
  trait None {
    def unit: Option[String] = None
  }
  trait Percentage {
    def unit: Option[String] = Some("percentage")
  }
  trait DegreesCelsius {
    def unit: Option[String] = Some("celsius")
  }
  trait ArcDegrees {
    def unit: Option[String] = Some("arcdegrees")
  }
  trait Lux {
    def unit: Option[String] = Some("lux")
  }
  trait Seconds {
    def unit: Option[String] = Some("seconds")
  }
}
