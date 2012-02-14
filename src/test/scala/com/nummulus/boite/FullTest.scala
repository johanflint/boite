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
  
  "Map the String.length function" should "return a full boite of type Int" in {
    boite.map(s => s.length) should equal (Full(JacquesBrel.length))
  }
  
  "Flat map the String.length function" should "return a full boite of type Int" in {
    boite.flatMap(s => Full(s.length)) should equal (Full(JacquesBrel.length))
  }
  
  "Foreach" should "be called on a full boite" in {
    val test = mock[DummyTrait]
    boite.foreach(s => test.calculate(s))
    
    verify (test) calculate(JacquesBrel)
  }
  
  "To list" should "return a list with one entry" in {
    boite.toList should equal (List(JacquesBrel))
  }
}