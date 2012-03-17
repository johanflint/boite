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
  
  "getOrElse" should "return the default value if empty" in {
    Empty.getOrElse(404) should equal (404)
  }
  
  "map" should "return Empty" in {
    Empty.map(s => s) should equal (Empty)
  }
  
  "flatMap" should "return Empty" in {
    Empty.flatMap(s => s) should equal (Empty)
  }
  
  "foreach" should "not call anything" in {
    val test = mock[DummyTrait]
    Empty.foreach(s => test.calculate(""))
    
    verify (test, never) calculate("")
  }
  
  "toList" should "return an empty list" in {
    Empty.toList should equal (List())
  }
}