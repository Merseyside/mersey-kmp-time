//
//  Persistence.swift
//  ios-app-swiftui
//
//  Created by Ivan on 22.08.2021.
//

import Foundation
import CoreData
import MultiPlatformLibrary

struct PersistenceController {
    static let shared = PersistenceController()

    static var preview: PersistenceController = {
        let result = PersistenceController(inMemory: true)
        let viewContext = result.container.viewContext
        for _ in 0..<10 {
            let newItem = Item(context: viewContext)
            newItem.timestamp = Date()
        }
        do {
            try viewContext.save()
        } catch {
            // Replace this implementation with code to handle the error appropriately.
            // fatalError() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development.
            let nsError = error as NSError
            fatalError("Unresolved error \(nsError), \(nsError.userInfo)")
        }
        return result
    }()

    let container: NSPersistentContainer

    init(inMemory: Bool = false) {
        let time = Time()
        let now = time.now
        let timeConfiguration = TimeConfiguration()
        
        NSLog("current time = %lld", now.value)
        NSLog("day of month = %d", TimeUnitExtKt.toDayOfMonth(now, timeZone: timeConfiguration.timeZone).value)
        NSLog("day of week = %d", TimeUnitExtKt.toDayOfWeek(now, timeZone: timeConfiguration.timeZone).index)
        TimeUnitExtKt.toDayOfWeekHuman(now, pattern: timeConfiguration.dayOfWeekPattern, timeZone: timeConfiguration.timeZone, language: timeConfiguration.language, country: timeConfiguration.country)
        NSLog("seconds of minute = %d", TimeUnitExtKt.toSecondsOfMinute(now, timeZone: timeConfiguration.timeZone).value)
        NSLog("minutes of hour = %d", TimeUnitExtKt.toMinutesOfHour(now, timeZone: timeConfiguration.timeZone).value)
        NSLog("hours of day = %d", TimeUnitExtKt.toHoursOfDay(now, timeZone: timeConfiguration.timeZone).value)
        NSLog("month = %d", TimeUnitExtKt.toMonth(now, timeZone: timeConfiguration.timeZone).index)
        
        
        container = NSPersistentContainer(name: "ios_app_swiftui")
        if inMemory {
            container.persistentStoreDescriptions.first!.url = URL(fileURLWithPath: "/dev/null")
        }
        container.loadPersistentStores(completionHandler: { (storeDescription, error) in
            if let error = error as NSError? {
                // Replace this implementation with code to handle the error appropriately.
                // fatalError() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development.

                /*
                Typical reasons for an error here include:
                * The parent directory does not exist, cannot be created, or disallows writing.
                * The persistent store is not accessible, due to permissions or data protection when the device is locked.
                * The device is out of space.
                * The store could not be migrated to the current model version.
                Check the error message to determine what the actual problem was.
                */
                fatalError("Unresolved error \(error), \(error.userInfo)")
            }
        })
    }
}
