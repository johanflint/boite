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
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar

@RunWith(classOf[JUnitRunner])
class FullTest extends FlatSpec with ShouldMatchers with MockitoSugar {
  val JacquesBrel = "Jacques Brel"

  val FullWithSinger = Full(JacquesBrel)
  
  behavior of "Full"
  
  it should "not be empty" in {
    FullWithSinger.isEmpty should be (false)
  }
  
  it should "not equal Empty" in {
    FullWithSinger == Empty should be (false)
  }
  
  it should "be defined" in {
    FullWithSinger.isDefined should be (true)
  }
  
  "get" should "return the value" in {
    FullWithSinger.get should be (JacquesBrel)
  }
  
  "getOrElse" should "return the value" in {
    FullWithSinger.getOrElse("Edith Piaf") should be (JacquesBrel)
  }
  
  "map" should "return a Full" in {
    FullWithSinger map { _.length } should be (Full(JacquesBrel.length))
  }
  
  "flatMap" should "return a full box" in {
    FullWithSinger flatMap { s => Full(s.length) } should be (Full(JacquesBrel.length))
  }
  
  "foreach" should "call a function on Full's value" in {
    val test = mock[DummyTrait]
    FullWithSinger foreach { s => test.calculate(s) }
    
    verify (test) calculate(JacquesBrel)
  }
  
  "toList" should "return a list with only Full's value" in {
    FullWithSinger.toList should be (List(JacquesBrel))
  }
}