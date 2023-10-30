package de.hs_fl.sbg.plugins.datafile_converter.converter.from

import org.junit.Test
import java.io.File

class ConvertFromJSONTest {

    @Test
    fun test () {
        val input = File("./src/test/kotlin/de/hs_fl/sbg/plugins/datafile_converter/converter/from/ConvertFromJSONTest.json")
        if (!input.exists()) input.createNewFile()

        input.writeText("{\"stringKey\": \"StringValue\", \"numberKey\": 1234, \"booleanKey\": true, \"nullKey\": null, \"arrayKey\": [1, \"string\", null, true, {}], \"objectKey\": { \"nestedString\": \"Nested\", \"nestedNumber\": 5678, \"nestedBoolean\": false, \"nestedNull\": null } }")

        ConvertFromJSON().convert(input)
        assert(true)
    }
}