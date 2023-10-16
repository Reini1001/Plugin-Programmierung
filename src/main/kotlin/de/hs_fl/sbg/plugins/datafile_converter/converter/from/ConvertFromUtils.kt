package de.hs_fl.sbg.plugins.datafile_converter.converter.from

class ConvertFromUtils {
    companion object {
        fun toTypeOrDefault(string: String): Any {
            when {
                string.toBooleanStrictOrNull() != null -> {
                    return string.toBooleanStrict()
                }
                string.toIntOrNull() != null -> {
                    return string.toInt()
                }
                string.toLongOrNull() != null -> {
                    return string.toLong()
                }
                string.toDoubleOrNull() != null -> {
                    return string.toDouble()
                }
                else -> {
                    return string
                }
            }
        }
    }
}