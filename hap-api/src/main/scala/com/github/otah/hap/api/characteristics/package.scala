package com.github.otah.hap.api

package object characteristics {

  /** Traits to mix into characteristics, which define the standard names of the units */
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

  trait ReadNotify {
    this: Characteristic =>

    override final def isReadable: Boolean = true
    override final def isWritable: Boolean = false
    override final def hasEvents: Boolean = true
  }

  trait ReadWriteNotify {
    this: Characteristic =>

    override final def isReadable: Boolean = true
    override final def isWritable: Boolean = true
    override final def hasEvents: Boolean = true
  }
}
