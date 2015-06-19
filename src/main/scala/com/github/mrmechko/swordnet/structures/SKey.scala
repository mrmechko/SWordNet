package com.github.mrmechko.swordnet.structures
import com.github.mrmechko.swordnet.SWordNet


sealed trait SKey {
  def key : String
  def offset : Int
  def lemma : String
  def pos : SPos
  def senseNumber : Int
  def synset : SSynset
  def id : String = "%s#%s#%d".format(lemma, pos.asChar, senseNumber)
  def hasLexical(relation : SRelationType) : Seq[SKey]
  def hasSemantic(relation : SRelationType) : Seq[SSynset] = synset.hasSemantic(relation)
  override def toString : String = "SKey(%s)".format(key)
  def definition : String
  def tagCount : Int
}

case class SenseInfo(pos : SPos, synsetid: Int, senseid : Int, sensenum : Short, lemma : String, definition : String, tagCount : Int)

object SKey {

  private case class SKeyImpl(key : String, info : SenseInfo) extends SKey {
    override def lemma: String = info.lemma

    override def synset: SSynset = SSynset(this)

    override def senseNumber: Int = info.sensenum

    override def hasLexical(relation: SRelationType): Seq[SKey] = ???

    override def pos: SPos = info.pos

    override def definition: String = info.definition

    override def offset: Int = info.synsetid

    override def tagCount: Int = info.tagCount
  }

  def apply(query : String) : SKey = {
    SWordNet.getk2S(query) match {
      case Some(x) => x
      case None => SWordNet.wpn2S(query)
    }
  }

  def apply(key : String, info : SenseInfo) : SKey = {
    SKeyImpl(key, info)
  }

  def get(query : String) : Option[SKey] = {
    val k = SWordNet.getk2S(query)
    k match {
      case Some(x) => k
      case None => SWordNet.getwpn2S(query)
    }
  }
}

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

  def apply(offset : Int, pos : SPos) : SSynset = {
    SSynsetImpl(SWordNet.o2S(offset))
  }

  def get(offset : Int) : Option[SSynset] = {
    val v = SWordNet.o2S(offset)
    if(v.size > 0) Some(SSynsetImpl(SWordNet.o2S(offset)))
    else None
  }

  def apply(skey : SKey) : SSynset = {
    SSynsetImpl(SWordNet.o2S(skey.offset))
  }
}

