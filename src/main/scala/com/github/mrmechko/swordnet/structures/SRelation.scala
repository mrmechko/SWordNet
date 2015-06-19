package com.github.mrmechko.swordnet.structures

/**
 * Created by mechko on 6/19/15.
 */
trait SRelation {
  def apply(key : SKey) : Seq[SKey]
}

trait SRelationType {
  def name : String
  def linkid : Short
  def isRecursive : Boolean
}

object SRelationType {

  private case class SRelationTypeImpl(name : String, linkid : Short, recursive : Boolean) extends SRelationType {
    override def toString : String = "SRelationType(%s)".format(name)
    override def isRecursive : Boolean = recursive
  }
  /**
   * Pertainym and Derived_from_adjective are mapped to the same symbol
   */
  val domain_member_category : SRelationType = SRelationTypeImpl("domain_member_category", 92, recursive = false)
  val hypernym : SRelationType = SRelationTypeImpl("hypernym", 1, recursive = true)
  val instance_hyponym : SRelationType = SRelationTypeImpl("instance_hyponym", 4, recursive = true)
  val pertainym : SRelationType = SRelationTypeImpl("pertainym", 80, recursive = false)
  val also_see : SRelationType = SRelationTypeImpl("also", 50, recursive = false)
  val domain_region : SRelationType = SRelationTypeImpl("domain_region", 93, recursive = false)
  val antonym : SRelationType = SRelationTypeImpl("antonym", 30, recursive = false)
  val similar_to : SRelationType = SRelationTypeImpl("similar", 40, recursive = false)
  val verb_group : SRelationType = SRelationTypeImpl("verb_group", 70, recursive = false)
  val entailment : SRelationType = SRelationTypeImpl("entail", 21, recursive = true)
  val substance_holonym : SRelationType = SRelationTypeImpl("substance_holonym", 15, recursive = true)
  val member_meronym : SRelationType = SRelationTypeImpl("member_meronym", 14, recursive = true)
  val derivationally_related_form : SRelationType = SRelationTypeImpl("derivation", 81, recursive = false)
  val domain_usage : SRelationType = SRelationTypeImpl("domain_usage", 95, recursive = false)
  val part_holonym : SRelationType = SRelationTypeImpl("part_holonym", 11, recursive = true)
  val part_meronym : SRelationType = SRelationTypeImpl("part_meronym", 12, recursive = true)
  val member_topic : SRelationType = SRelationTypeImpl("member", 98, recursive = false)
  val member_holonym : SRelationType = SRelationTypeImpl("member_holonym", 13, recursive = true)
  val domain_member_region : SRelationType = SRelationTypeImpl("domain_member_region", 94, recursive = false)
  val substance_meronym : SRelationType = SRelationTypeImpl("substance_meronym", 16, recursive = true)
  val domain_topic : SRelationType = SRelationTypeImpl("domain", 97, recursive = false)
  val hyponym : SRelationType = SRelationTypeImpl("hyponym", 2, recursive = true)
  val instance_hypernym : SRelationType = SRelationTypeImpl("instance_hypernym", 3, recursive = true)
  val domain_member_usage : SRelationType = SRelationTypeImpl("domain_member_usage", 96, recursive = false)
  val cause : SRelationType = SRelationTypeImpl("cause", 23, recursive = true)
  val attribute : SRelationType = SRelationTypeImpl("attribute", 60, recursive = false)
  val participle_of_verb : SRelationType = SRelationTypeImpl("participle", 71, recursive = false)
  val domain_category : SRelationType = SRelationTypeImpl("domain_category", 91, recursive = false)



  val symbolToRelation = Map[String, SRelationType](
    "&"-> similar_to,
    "<"-> participle_of_verb,
    "\\"->pertainym,
    "*"->entailment,
    ">"->cause,
    "^"->also_see,
    "$"->verb_group,
    "!"->antonym,
    "@"->hypernym,
    "@i"->instance_hypernym,
    "~"->hyponym,
    "~i"->instance_hyponym,
    "#m"->member_holonym,
    "#s"->substance_holonym,
    "#p"->part_holonym,
    "%m"->member_meronym,
    "%s"->substance_meronym,
    "%p"->part_meronym,
    "="->attribute,
    "+"->derivationally_related_form,
    ";c"->domain_topic,
    "-c"->member_topic,
    ";r"->domain_region,
    "-r"->domain_member_region,
    ";u"->domain_usage,
    "-u"->domain_member_usage
  )

  /*
  @todo: Add lists for parts of speech
   */
}