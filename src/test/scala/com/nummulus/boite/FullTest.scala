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
  
  "Full" should "be defined" in {
    box.isDefined should equal (true)
  }
  
  "Open or" should "return the value if full" in {
    box.getOrElse("Edith Piaf") should equal (JacquesBrel)
  }
  
  "Two full boxes with the same content" should "be equal" in {
    box == Full(JacquesBrel) should be (true)
  }
  
  "Full and its raw value" should "be equal" in {
    box == JacquesBrel should be (true)
  }
  
  "Full" should "not be Empty" in {
    box == Empty should be (false)
  }
  
  "Map the String.length function" should "return a full box of type Int" in {
    box.map(s => s.length) should equal (Full(JacquesBrel.length))
  }
  
  "Flat map the String.length function" should "return a full box of type Int" in {
    box.flatMap(s => Full(s.length)) should equal (Full(JacquesBrel.length))
  }
  
  "Foreach" should "be called on a full box" in {
    val test = mock[DummyTrait]
    box.foreach(s => test.calculate(s))
    
    verify (test) calculate(JacquesBrel)
  }
  
  "To list" should "return a list with one entry" in {
    box.toList should equal (List(JacquesBrel))
  }
}