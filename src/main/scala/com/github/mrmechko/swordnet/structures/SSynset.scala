package com.github.mrmechko.swordnet.structures

import com.github.mrmechko.swordnet.SWordNet

trait SSynset {
  def lemmas : Seq[String]
  def pos : SPos
  def keys : Seq[SKey]
  def head : SKey
  def offsetString : String
  def offset : Int
  def gloss : String
  def examples : Seq[String]
  def hasSemantic(relation : SRelationType) : Seq[SSynset]
  override def toString : String = "SSynset(%s)".format(head.key)
}

object SSynset {
  private case class SSynsetImpl(keys : Seq[SKey]) extends SSynset {
    override def lemmas: Seq[String] = keys.map(_.lemma)

    override def gloss: String = head.definition

    override def offset: Int = head.offset

    override def pos: SPos = head.pos

    override def offsetString: String = "%s%8d".format(pos.asChar, offset)

    override def examples: Seq[String] = ???

    val head: SKey = keys.sortBy(-_.tagCount).head

    override def hasSemantic(relation: SRelationType): Seq[SSynset] = SWordNet.semanticLinks(this, relation.linkid)
  }

  def fromOffset(offset : Int) : SSynset = SSynsetImpl(SWordNet.o2S(offset))

  def apply(offset : Int, pos : SPos) : SSynset = {
    SSynsetImpl(SWordNet.o2S(offset))
  }

  def get(offset : Int) : Option[SSynset] = {
    val v = SWordNet.o2S(offset)
    if(v.nonEmpty) Some(SSynsetImpl(SWordNet.o2S(offset)))
    else None

  }

  def fromSKey(skey : SKey) : SSynset = SSynsetImpl(SWordNet.o2S(skey.offset))

  def apply(skey : SKey) : SSynset = {
    SSynsetImpl(SWordNet.o2S(skey.offset))
  }
}
