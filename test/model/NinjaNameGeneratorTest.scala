package model

import org.specs2.Specification

class NinjaNameGeneratorTest extends Specification { def is = s2"""
 This is a specification to check the 'NinjaNameGenerator'
 NinjaNameGenerator.generate should
   return "" if name is ""                                                    $e1
   return "Tokimimo" if name is "nico"                                        $e2
   return "" if name is a number                                              $e3
   return "Tokimimotakaari Mikarukatatamo" if name is "Nicolas Cavallo"       $e4
                                                        """

  def e1 = NinjaNameGenerator.generate("") must beEqualTo("")
  def e2 = NinjaNameGenerator.generate("nico") must beEqualTo("Tokimimo")
  def e3 = NinjaNameGenerator.generate("1234") must beEqualTo("")
  def e4 = NinjaNameGenerator.generate("Nicolas Cavallo") must beEqualTo("Tokimimotakaari Mikarukatatamo")

}
