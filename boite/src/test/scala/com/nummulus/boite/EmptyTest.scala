/*
 * Copyright 2012 Nummulus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nummulus.boite

import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FlatSpec
import org.scalatest.mock.MockitoSugar

@RunWith(classOf[JUnitRunner])
class EmptyTest extends FlatSpec with ShouldMatchers with MockitoSugar {
  behavior of "Empty"
  
  it should "be empty" in {
    Empty.isEmpty should be (true)
  }
  
  it should "not be defined" in {
    Empty.isDefined should be (false)
  }
  
  "getOrElse" should "return the default value if empty" in {
    Empty.getOrElse(404) should be (404)
  }
  
  "map" should "return Empty" in {
    Empty map { s => s } should be (Empty)
  }
  
  "flatMap" should "return Empty" in {
    Empty flatMap { s => s } should be (Empty)
  }
  
  "foreach" should "not call anything" in {
    val test = mock[DummyTrait]
    Empty foreach { s => test.calculate("") }
    
    verify (test, never) calculate("")
  }
  
  "toList" should "return an empty list" in {
    Empty.toList should be (List())
  }
}