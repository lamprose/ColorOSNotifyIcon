/*
 * ColorOSNotifyIcon - Optimize notification icons for ColorOS and adapt to native notification icon specifications.
 * Copyright (C) 2019-2022 Fankes Studio(qzmmcn@163.com)
 * https://github.com/fankes/ColorOSNotifyIcon
 *
 * This software is non-free but opensource software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either
 * version 3 of the License, or any later version.
 * <p>
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * and eula along with this software.  If not, see
 * <https://www.gnu.org/licenses/>
 *
 * This file is Created by fankes on 2022/1/24.
 */
@file:Suppress("unused")

package com.fankes.coloros.notify.application

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import me.weishu.reflection.Reflection

class CNNApplication : Application() {

    companion object {

        /** 全局静态实例 */
        private var context: CNNApplication? = null

        /** 调用全局静态实例 */
        val appContext get() = context ?: error("App is death")
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        /** 解锁隐藏 API */
        Reflection.unseal(base)
    }

    override fun onCreate() {
        super.onCreate()
        /** 设置静态实例 */
        context = this
        /** 跟随系统夜间模式 */
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }
}