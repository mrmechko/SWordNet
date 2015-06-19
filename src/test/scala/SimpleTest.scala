import com.github.mrmechko.swordnet.structures.{SRelationType, SPos, SKey}
import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by mechko on 6/19/15.
 */
class TypeTests extends FlatSpec with Matchers {

  "An SKey" should "look like an SKey" in {
    (SKey("cat%1:05:00::").toString) shouldBe "SKey(cat%1:05:00::)"
    (SKey("cat#n#1").toString) shouldBe "SKey(cat%1:05:00::)"
  }

  "An SPos" should "look like an SPos" in {
    (SPos(1).toString) shouldBe "SPos(noun)"
    (SPos(2).toString) shouldBe "SPos(verb)"
    (SPos(3).toString) shouldBe "SPos(adjective)"
    (SPos(4).toString) shouldBe "SPos(adverb)"
    (SPos(5).toString) shouldBe "SPos(satellite)"
    (SPos(6).toString) shouldBe "SPos(other)"

    SPos('n') shouldBe SPos("noun")
    SPos(1) shouldBe SPos("noun")

    SPos('v') shouldBe SPos("verb")
    SPos(2) shouldBe SPos("verb")

    SPos('r') shouldBe SPos("adverb")
    SPos(4) shouldBe SPos("adverb")

    SPos('a') shouldBe SPos("adjective")
    SPos(3) shouldBe SPos("adjective")

    SPos('s') shouldBe SPos("satellite")
    SPos(5) shouldBe SPos("satellite")
  }

  "cat%1:05:00::" should "be render SKeys" in {
    val cat = SKey("cat%1:05:00::")
    cat.key shouldBe "cat%1:05:00::"
    cat.lemma shouldBe "cat"
    cat.pos shouldBe SPos('n')
    cat.definition shouldBe "feline mammal usually having thick soft fur and no ability to roar: domestic cats; wildcats"
    cat.senseNumber shouldBe 1
    cat.id shouldBe "cat#n#1"

    cat.synset.keys shouldBe(Seq(cat, SKey("true_cat%1:05:00::")))
    cat.hasSemantic(SRelationType.hypernym).map(_.head).foreach(println)
  }


}
