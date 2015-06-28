package com.github.mrmechko.swordnet

import com.github.mrmechko.swordnet.dao.{WordNetLoader}
import com.github.mrmechko.swordnet.structures.{SenseInfo, SSynset, SPos, SKey}

object SWordNet {

  private val _semanticLinks : Map[(Short, Int), Seq[Int]] = WordNetLoader.loadSemanticLinks.groupBy(x => (x._1, x._2)).mapValues(_.map(_._3)).withDefaultValue(List())


  private val _k2S = SKey.keys.map(s => s.key -> s).toMap

  /**
   * Create a SKey from a %-formatted sense key
   * @param key
   * @return SKey(key)
   */
  def k2S(key : String) : SKey = {
    _k2S(key)
  }

  def getk2S(key : String) : Option[SKey] = _k2S.get(key)

  private val _l2S = SKey.keys.groupBy(_.lemma)

  /**
   * get all SKeys which correspond to lemma
   * @param lemma
   * @return a Seq of SKeys
   */
  def l2S(lemma : String) : Seq[SKey] = _l2S.getOrElse(lemma, Seq())

  private val _lp2S = SKey.keys.groupBy(k => (k.lemma, k.pos))
  /**
   * Get all SKeys which correspond to a lemma and a part of speech
   * @param lemma
   * @param pos
   * @return a Seq of SKeys
   */
  def lp2S(lemma : String, pos : SPos) : Seq[SKey] = _lp2S.getOrElse((lemma,pos), Seq())

  private val _wpn2S = SKey.keys.map(k => k.id -> k).toMap
  /**
   * get the SKey corresponding to lemma.pos.sensenumber
   * @param lemma
   * @param pos
   * @param sensenum
   * @return the SKey
   */
  def wpn2S(lemma : String, pos : SPos, sensenum : Int) : SKey = _wpn2S("%s#%s#%d".format(lemma, pos.asChar, sensenum))
  def wpn2S(id : String) : SKey = _wpn2S(id)


  def getwpn2S(lemma : String, pos : SPos, sensenum : Int) : Option[SKey] = _wpn2S.get("%s#%s#%d".format(lemma, pos.asChar, sensenum))
  def getwpn2S(id : String) : Option[SKey] = _wpn2S.get(id)

  private val _o2s = SKey.keys.groupBy(_.offset)

  /**
   * Get a synset by offset and pos (offsets from wnsql already include the posnumber, so offsets are actually unique)
   * @param offset
   * @return the synset
   */
  def o2S(offset : Int) : Seq[SKey] = _o2s(offset)

  def semanticLinks(synset : SSynset, link : Short) : Seq[SSynset] = {
    _semanticLinks((link, synset.offset)).map(SSynset.get(_)).collect{case Some(x) => x}
  }
}
