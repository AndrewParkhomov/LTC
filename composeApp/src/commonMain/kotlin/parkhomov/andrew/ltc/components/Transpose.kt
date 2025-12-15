package parkhomov.andrew.ltc.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

val Icons.Transpose: ImageVector
    get() {
        if (_transpose != null) {
            return _transpose!!
        }
        _transpose = materialIcon(name = "Transpose") {
            materialPath {
                // Плюс внизу справа
                moveTo(15f, 17f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(-2f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(-2f)
                horizontalLineToRelative(-2f)
                verticalLineToRelative(-2f)
                horizontalLineToRelative(-2f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(-2f)
                verticalLineToRelative(2f)
                close()
                
                // Зовнішня рамка
                moveTo(20f, 2f)
                lineTo(4f, 2f)
                curveToRelative(-1.1f, 0f, -2f, 0.9f, -2f, 2f)
                verticalLineToRelative(16f)
                curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
                horizontalLineToRelative(16f)
                curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
                lineTo(22f, 4f)
                curveToRelative(0f, -1.1f, -0.9f, -2f, -2f, -2f)
                close()
                
                // Мінус вгорі зліва
                moveTo(5f, 5f)
                horizontalLineToRelative(6f)
                verticalLineToRelative(2f)
                lineTo(5f, 7f)
                close()
                
                // Діагональна лінія (заповнений трикутник)
                moveTo(20f, 20f)
                lineTo(4f, 20f)
                lineTo(20f, 4f)
                verticalLineToRelative(16f)
                close()
            }
        }
        return _transpose!!
    }

private var _transpose: ImageVector? = null