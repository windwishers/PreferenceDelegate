package fail.toepic.preferencedelegate.ui.model

import com.dino.library.dinorecyclerview.ItemViewType
import fail.toepic.preferencedelegate.R

data class ButtonItem(val title : String = "", val onClick: (ButtonItem) -> Unit = {}) : ItemViewType{
    override val itemLayoutResId: Int
        get() = R.layout.item_button
}