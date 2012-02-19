package com.nummulus.boite

import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar

@RunWith(classOf[JUnitRunner])
class FullTest extends FlatSpec with ShouldMatchers with MockitoSugar {
  val JacquesBrel = "Jacques Brel"

  val box = Full(JacquesBrel)
  
  "Full" should "not be empty" in {
    box.isEmpty should equal (false)
  }
  
  "Full" should "not be Empty" in {
    box == Empty should be (false)
  }
  
  "Full" should "be defined" in {
    box.isDefined should equal (true)
  }
  
  "getOrElse" should "return the value if full" in {
    box.getOrElse("Edith Piaf") should equal (JacquesBrel)
  }
  
  "map" should "return a full box" in {
    box.map(s => s.length) should equal (Full(JacquesBrel.length))
  }
  
  "flatMap" should "return a full box" in {
    box.flatMap(s => Full(s.length)) should equal (Full(JacquesBrel.length))
  }
  
  "foreach" should "call a function on its value" in {
    val test = mock[DummyTrait]
    box.foreach(s => test.calculate(s))
    
    verify (test) calculate(JacquesBrel)
  }
  
  "toList" should "return a list with one entry" in {
    box.toList should equal (List(JacquesBrel))
  }
}