package snapple.crdts.datatypes

sealed trait SupportedValueType

case object BooleanValueType extends SupportedValueType

case object ByteValueType extends SupportedValueType

case object IntValueType extends SupportedValueType

case object LongValueType extends SupportedValueType

case object DoubleValueType extends SupportedValueType

case object StringValueType extends SupportedValueType
