### Amazon Fire 7 (2019, 9th Generation) (mustang)

* failing to connect to wifi will unlock "skip setup" button
* try to install twrp before bricking
* boot loop cannot be stopped by buttons, open the cover and disconnect the battery

### firmware

* [update-kindle-Fire_7_9th_Gen-NS6312_user_1852_0002517056644.bin](https://web.archive.org/web/20191006104203/https://www.amazon.com/gp/help/customer/display.html?nodeId=201357170)
* [update-kindle-NS6312_user_1827_0002517050244.bin](https://example.com//fireos-tablet-src.s3.amazonaws.com/Tr37jJbMSR96z5WBmVbW6uq32p/update-kindle-NS6312_user_1827_0002517050244.bin) works with mtk-su
* FireOS 7.3.1.8 - FireOS 7.3.2.9 - don't work with twrp

### customizing

disable updates
```bash
adb shell
pm disable-user com.amazon.device.software.ota
pm disable-user com.amazon.device.software.ota.override
pm disable-user com.amazon.kindle.otter.oobe.forced.ota
```

replace launcher
```bash
adb install launcher.apk
adb shell pm disable-user com.amazon.firelauncher
```

disable subscription-only apps
```bash
adb shell
pm disable-user com.amazon.ags.app
pm disable-user com.amazon.avod
pm disable-user com.amazon.dee.app
pm disable-user com.amazon.geo.client.maps
pm disable-user com.amazon.kindle
pm disable-user com.amazon.kindle.otter.oobe
pm disable-user com.amazon.mp3
pm disable-user com.amazon.photos
pm disable-user com.amazon.photos.importer
pm disable-user com.amazon.tahoe
pm disable-user com.amazon.venezia
pm disable-user com.amazon.webapp
pm disable-user com.amazon.windowshop
pm disable-user com.android.calendar
pm disable-user com.android.contacts
pm disable-user com.android.email
pm disable-user com.audible.application.kindle
pm disable-user com.goodreads.kindle
```

| App ID                         | App name       |
| ------------------------------ | -------------- |
| com.amazon.ags.app             | Games          |
| com.amazon.avod                | Video          |
| com.amazon.cloud9              | Silk Browser   |
| com.amazon.dee.app             | Alexa          |
| com.amazon.geo.client.maps     | Maps           |
| com.amazon.kindle              | Books          |
| com.amazon.kindle.otter.oobe   | Device Setup   |
| com.amazon.mp3                 | Music          |
| com.amazon.photos              | Amazon Photos  |
| com.amazon.photos.importer     | Photo Importer |
| com.amazon.tahoe               | FreeTime       |
| com.amazon.venezia             | Appstore       |
| com.amazon.webapp              | Kindle Store   |
| com.amazon.windowshop          | Shop Amazon    |
| com.android.calendar           | Calendar       |
| com.android.contacts           | Contacts       |
| com.android.email              | Email          |
| com.audible.application.kindle | Audible        |
| com.goodreads.kindle           | Goodreads      |

```bash
adb shell wm density 200
```
