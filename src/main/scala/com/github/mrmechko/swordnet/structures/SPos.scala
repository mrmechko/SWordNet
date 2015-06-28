package com.github.mrmechko.swordnet.structures
/**
 * asChar and asInt are only valid for wordnet pos. For general use, string is required
 **/
sealed trait SPos {
  def asChar : Char
  def asString : String
  def asInt : Int
  def isWN : Boolean
  //Compare asChar first, then as String. May be sufficient to compare as string if abbreviations are properly resolved
  override def equals(o : Any) : Boolean = o match {
    case that :  SPos => this.toString.equals(that.toString)
    case _ => false
  }
}

object SPos {
  val wnp : Set[Char] = Set('n', 'v', 'a', 'r')
  val c2s : Map[Char, String] = Map('n'->"noun", 'v'->"verb", 'a'->"adjective", 'r'->"adverb", 's'->"satellite", 'p'->"preposition").withDefaultValue("other")
  val c2i : Map[Char, Int] = Map('n'->1, 'v'->2, 'a'->3, 'r'->4, 's'->5).withDefaultValue(6)
  val s2c : Map[String, Char] = c2s.map(_.swap).withDefaultValue('_')
  val i2c : Map[Int, Char] = c2i.map(_.swap).withDefaultValue('_')

  val abbr : Map[String, String] = Map("adj" -> "adjective", "adv" -> "adverb", "sat" -> "satellite", "prep"->"preposition", "pro" -> "preposition")

  private case class SPosCharImpl(asChar : Char) extends SPos {
    override def toString: String = "SPos(%s)".format(asString)
    override def asString: String = c2s(asChar)
    override def asInt: Int = c2i(asChar)
    override def isWN : Boolean = wnp.contains(asChar)
  }

  private case class SPosStringImpl(asString : String) extends SPos {
    override def toString : String = "SPos(%s)".format(asString)
    override def asChar : Char = s2c(asString)
    override def asInt : Int = c2i(asChar)
    override def isWN : Boolean = wnp.contains(asChar)
  }

  def apply(int : Int) : SPos = SPosCharImpl(i2c(int))
  //STrips has prefixes
  def apply(string : String) : SPos = if(string.length > 1) {
    SPosStringImpl(abbr.getOrElse(string.toLowerCase(), string.toLowerCase))
  } else SPos(string.head)
  def apply(char : Char) : SPos = SPosCharImpl(char.toLower)

}
