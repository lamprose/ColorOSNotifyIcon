/*
 * ColorOSNotifyIcon - Optimize notification icons for ColorOS and adapt to native notification icon specifications.
 * Copyright (C) 20174 Fankes Studio(qzmmcn@163.com)
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
 * This file is created by fankes on 2023/4/17.
 */
package com.fankes.coloros.notify.utils.tool

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import androidx.core.graphics.drawable.toBitmap
import com.fankes.coloros.notify.R
import com.fankes.coloros.notify.utils.factory.appIconOf
import com.fankes.coloros.notify.wrapper.BuildConfigWrapper

/**
 * 模块更新激活提醒通知工具类
 */
object ActivationPromptTool {

    /** 当前模块的包名 */
    private const val MODULE_PACKAGE_NAME = BuildConfigWrapper.APPLICATION_ID

    /** 推送通知的渠道名称 */
    private const val NOTIFY_CHANNEL = "activationPromptId"

    /**
     * 推送提醒通知
     * @param context 当前实例
     * @param packageName 当前 APP 包名
     */
    fun prompt(context: Context, packageName: String) {
        if (packageName != BuildConfigWrapper.APPLICATION_ID) return
        context.getSystemService(NotificationManager::class.java)?.apply {
            createNotificationChannel(
                NotificationChannel(
                    NOTIFY_CHANNEL, "ColorOS 通知图标增强 - 版本更新",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply { enableLights(false) }
            )
            notify(packageName.hashCode(), Notification.Builder(context, NOTIFY_CHANNEL).apply {
                setShowWhen(true)
                setContentTitle("模块已更新")
                setContentText("点按通知打开模块以完成新版本激活。")
                setColor(0xFF4E8A5A.toInt())
                setAutoCancel(true)
                setSmallIcon(Icon.createWithResource(MODULE_PACKAGE_NAME, R.drawable.ic_notify_update))
                setLargeIcon(context.appIconOf(packageName)?.toBitmap())
                setContentIntent(
                    PendingIntent.getActivity(
                        context, packageName.hashCode(),
                        Intent().apply {
                            component = ComponentName(MODULE_PACKAGE_NAME, "$MODULE_PACKAGE_NAME.ui.activity.MainActivity")
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }, if (Build.VERSION.SDK_INT < 31) PendingIntent.FLAG_UPDATE_CURRENT else PendingIntent.FLAG_IMMUTABLE
                    )
                )
            }.build())
        }
    }
}