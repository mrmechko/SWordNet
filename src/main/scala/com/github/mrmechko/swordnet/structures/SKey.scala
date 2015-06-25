package com.github.mrmechko.swordnet.structures
import com.github.mrmechko.swordnet.SWordNet
import com.github.mrmechko.swordnet.dao.WordNetLoader


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

  private val senseinfos : Map[String, SenseInfo] = WordNetLoader.loadSenseInfos

  val keys : Seq[SKey] = senseinfos.keys.map(SKeyImpl(_)).toSeq

  private case class SKeyImpl(key : String) extends SKey {
    private val info : SenseInfo = senseinfos(key)
    override def lemma: String = info.lemma

    override def synset: SSynset = SSynset(this)

    override def senseNumber: Int = info.sensenum

    override def hasLexical(relation: SRelationType): Seq[SKey] = ???

    override def pos: SPos = info.pos

    override def definition: String = info.definition

    override def offset: Int = info.synsetid

    override def tagCount: Int = info.tagCount
  }

  def apply(key : String) : SKey = SKeyImpl(key)

  def unapply(key : SKey) : Option[String] = Some(key.key)

  def from(query : String) : SKey = {
    SWordNet.getk2S(query) match {
      case Some(x) => x
      case None => SWordNet.wpn2S(query)
    }
  }

  def get(query : String) : Option[SKey] = {
    val k = SWordNet.getk2S(query)
    k match {
      case Some(x) => k
      case None => SWordNet.getwpn2S(query)
    }
  }
}
