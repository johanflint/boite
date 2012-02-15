package com.nummulus.boite

import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FlatSpec
import org.scalatest.mock.MockitoSugar

@RunWith(classOf[JUnitRunner])
class EmptyTest extends FlatSpec with ShouldMatchers with MockitoSugar {
  "Empty" should "be empty" in {
    Empty.isEmpty should equal (true)
  }
  
  "Empty" should "not be defined" in {
    Empty.isDefined should equal (false)
  }
  
  "Open or" should "return the default value if empty" in {
    Empty.openOr(404) should equal (404)
  }
  
  "Two empty boxes" should "be equal" in {
    Empty == Empty should be (true)
  }
  
  "Map" should "return Empty" in {
    Empty.map(s => s) should equal (Empty)
  }
  
  "Flat map" should "return Empty" in {
    Empty.flatMap(s => s) should equal (Empty)
  }
  
  "Foreach" should "not call anything" in {
    val test = mock[DummyTrait]
    Empty.foreach(s => test.calculate(""))
    
    verify (test, never) calculate("")
  }
  
  "To list" should "return an empty list" in {
    Empty.toList should equal (List())
  }
}