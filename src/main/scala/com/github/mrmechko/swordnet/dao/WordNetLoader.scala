package com.github.mrmechko.swordnet.dao

import com.github.mrmechko.swordnet.structures.{SPos, SenseInfo, SKey}

import scala.io.Source

/**
 * Created by mechko on 6/19/15.
 */
object WordNetLoader {

  def loadSenseInfos : Map[String, SenseInfo] = {
    Source.fromURL(getClass.getClassLoader().getResource("wn31.sensekeys"))
      .getLines().drop(4).grouped(4).map(entry => {
        val key = entry(0).stripLineEnd
        val si = entry(1).stripLineEnd.split("\t")
        val d = entry(2).stripLineEnd
        key -> SenseInfo(
          pos = SPos(si(1)),
          synsetid = si(2).toInt,
          senseid = si(3).toInt,
          sensenum = si(4).toShort,
          tagCount = si(5).toInt,
          definition = d,
          lemma = si(0)
        )
      }).toMap
  }
  def loadSemanticLinks : Seq[(Short, Int, Int)] = {
    Source.fromURL(getClass.getClassLoader().getResource("semantic.wn31.links"))
    .getLines().drop(1).map(entry => {
      val r = entry.stripLineEnd.split("\t")
      (r(0).toShort, r(1).toInt, r(2).toInt)
    }).toSeq
  }

  def loadLexicalLinks : Seq[(Short, Int, Int, Int, Int)] = {
    Source.fromURL(getClass.getClassLoader().getResource("lexical.wn31.links"))
      .getLines().drop(1).map(entry => {
      val r = entry.stripLineEnd.split("\t")
      (r(0).toShort, r(1).toInt, r(2).toInt, r(3).toInt, r(4).toInt)
    }).toSeq
  }

}
