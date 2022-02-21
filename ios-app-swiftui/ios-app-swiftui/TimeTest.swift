//
//  TimeTest.swift
//  ios-app-swiftui
//
//  Created by Ivan Sablin on 21.02.2022.
//

import Foundation
import KotlinTime

class TimeTest {
    static let timeTest = TimeTest()
    
    init() {
        let time = Time()
        let now = time.nowGMT
        let timeConfiguration = TimeConfiguration()
        
        NSLog("current time = %lld", now.value)
        NSLog("day of month = %d", TimeUnitExtKt.toDayOfMonth(now).value)
        NSLog("day of week = %d", TimeUnitExtKt.toDayOfWeek(now).index)
        TimeUnitExtKt.toDayOfWeekHuman(now, pattern: timeConfiguration.dayOfWeekPattern, language: timeConfiguration.language, country: timeConfiguration.country)
        NSLog("seconds of minute = %d", TimeUnitExtKt.toSecondsOfMinute(now).value)
        NSLog("minutes of hour = %d", TimeUnitExtKt.toMinutesOfHour(now).value)
        NSLog("hours of day = %d", TimeUnitExtKt.toHoursOfDay(now).value)
        NSLog("month = %d", TimeUnitExtKt.toMonth(now).index)
    }
}
