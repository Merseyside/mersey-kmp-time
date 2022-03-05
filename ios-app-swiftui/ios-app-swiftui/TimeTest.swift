//
//  TimeTest.swift
//  ios-app-swiftui
//
//  Created by Ivan Sablin on 21.02.2022.
//

import Foundation
import KotlinTime
import SwiftUI

class TimeTest {
    static let timeTest = TimeTest()
    
    init() {
        let time = Time()
        let nowZoned = time.now
        let now = time.nowGMT
        let timeConfiguration = TimeConfiguration()
        
        //guard let timeZone = try? KotlinTimeZone.companion.of(zoneId: "GMT") else { fatalError() }
        //time.getCurrentYear(timeZone: timeZone)
        
        NSLog("zoned %lld", nowZoned.gmtTimeUnit.value)
        NSLog(nowZoned.timeZone.zoneId)
        NSLog("offset = %lld", nowZoned.timeZone.offset.value)
        nowZoned.applyToTimeUnit(block: { (timeUnit: TimeUnit) -> TimeUnit in
            return timeUnit
        })
        
        let source = "2011-12-03T15:15:30+03:00"
        guard let sourceTZ = try? ZonedTimeUnit.companion.of(date: source, pattern: nil) else { fatalError() }
        NSLog(sourceTZ.gmtTimeUnit.getDebugString())
        let formatted = nowZoned.toFormattedDate(pattern: Pattern.OffsetISO_OFFSET_FULL_TIME.shared)
        NSLog(formatted.date)
        
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
