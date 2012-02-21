package com.nummulus.boite

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BoxTest extends FlatSpec with ShouldMatchers {
  val GilbertBecaud = "Gilbert Bécaud"
  val NotSupported = "not supported"
  val Exception = new UnsupportedOperationException(NotSupported)
  val Error = new AssertionError
  
  "apply" should "return Empty when it is passed null" in {
    Box(null) should be (Empty)
  }
  
  it should "return Full(value) when it is passed a value" in {
    Box(GilbertBecaud) should equal (Full(GilbertBecaud))
  }
  
  it should "return Failure when an exception is thrown while evaluating the expression passed to it" in {
    Box(throw Exception) should equal (Failure("not supported", Full(Exception)))
  }
  
  it should "let Errors pass through" in {
    intercept[AssertionError] {
      Box(throw Error)
    }
  }
  
  it should "accept an empty Option and return Empty" in {
    Box(None) should equal (Empty)
  }
  
  it should "accept a full Option and return Full" in {
    Box(Some(GilbertBecaud)) should equal (Full(GilbertBecaud))
  }
}