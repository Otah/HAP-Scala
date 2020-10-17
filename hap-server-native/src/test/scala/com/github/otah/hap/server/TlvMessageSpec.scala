package com.github.otah.hap.server

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TlvMessageSpec extends AnyFlatSpec {

  def input(bytes: Byte*)(results: TypeAndValue*) = assertResult(results)(TlvMessage(bytes).chunks)

  it should "parse typical payload for pair-setup" in input(0, 1, 0, 6, 1, 1)(
    TypeAndValue(0, Seq(0)),
    TypeAndValue(6, Seq(1)),
  )

  it should "ignore if there are no more bytes even though they were declared by length" in input(1, 3, 8, 9)(
    TypeAndValue(1, Seq(8, 9)),
  )

  it should "correctly recognize the end of first value by length" in input(9, 3, 1, 3, 5, 7, 1, 8)(
    TypeAndValue(9, Seq(1, 3, 5)),
    TypeAndValue(7, Seq(8)),
  )

  it should "simply continue if length defines zero" in input(5, 0, 8, 3, 2, 1, 0)(
    TypeAndValue(5, Seq()),
    TypeAndValue(8, Seq(2, 1, 0)),
  )
}
