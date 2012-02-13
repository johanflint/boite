package com.nummulus.boite

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class FullTest extends FlatSpec with ShouldMatchers {
  val JacquesBrel = "Jacques Brel"

  val boite = Full(JacquesBrel)
  
  "Full" should "not be empty" in {
    boite.isEmpty should equal (false)
  }
  
  "Full" should "be defined" in {
    boite.isDefined should equal (true)
  }
  
  "Open or" should "return the value if full" in {
    boite.openOr("Edith Piaf") should equal (JacquesBrel)
  }
  
  "Two full boites with the same content" should "be equal" in {
    boite == Full(JacquesBrel) should be (true)
  }
  
  "Full and its raw value" should "be equal" in {
    boite == JacquesBrel should be (true)
  }
  
  "Full" should "not be Empty" in {
    boite == Empty should be (false)
  }
}