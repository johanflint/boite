package com.nummulus.boite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FlatSpec

@RunWith(classOf[JUnitRunner])
class EmptyTest extends FlatSpec with ShouldMatchers  {
  "Empty" should "be empty" in {
    Empty.isEmpty should equal (true)
  }
  
  "Empty" should "not be defined" in {
    Empty.isDefined should equal (false)
  }
  
  "Open or" should "return the default value if empty" in {
    Empty.openOr(404) should equal (404)
  }
  
  "Two empty boites" should "be equal" in {
    Empty == Empty should be (true)
  }
}