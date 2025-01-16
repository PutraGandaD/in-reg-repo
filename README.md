## In-Reg
Simple Android App <br>
| APK Name  | Download Link |
| ------------- | ------------- |
| Latest Debug APK (Signed) | [See APK Files](https://github.com/PutraGandaD/in-reg-repo/blob/mvvm/app-debug.apk)  |

| Implementation  | Branch Name |
| ------------- | ------------- |
| MVVM Architecture | mvvm  |
| Clean Architecture with Modularization  | Coming Soon!  |

## Implementation
- [x] Model-View-ViewModel Architecture and Single Activity Approach 
- [x] Dependency Injection with Dagger-Hilt as recommended by Google
- [x] Reactive Programming Approach with Kotlin Coroutines (StateFlow, Flow, etc)
- [x] Using Paging 3.0 library for Pagination and Retrofit for Networking! 
- [x] UI built with Views and Material Design 3
- [x] Utilizing Single Source of Truth data source for better data management as recommended by Google
- [x] Utilizing Gradle Version Catalog for better dependency management 

## Dependencies
See Gradle Version Catalog [here](https://github.com/PutraGandaD/in-reg-repo/blob/mvvm/gradle/libs.versions.toml) for info on dependencies used in this project

## Edge Cases Handled
| Edge Cases |
| ------------- | 
| Backward compatible to Android 5.0 with Edge To Edge |
| Implemented Check Palindrome |
| Collect Data from API re-qr-es.i-n |
| Pull to refresh implemented |
| Pagination when scroll reached the end |
| Handled No Internet Connection with custom layout and Try Again button (timeout 10 second) |   
| Handled Server Timeout with custom layout and Try Again button (timeout 10 second)  | 
| Handled case where data is empty from API with custom layout and Try Again (timeout 10 second) |

## Project Structure (MVVM approach)
![Screen Shot 2025-01-17 at 1 31 09 AM](https://github.com/user-attachments/assets/6aa5fa94-f39c-45df-b10e-fb7e77d67c6d)

## Navigation Graph
![Screen Shot 2025-01-17 at 1 43 42 AM](https://github.com/user-attachments/assets/20828124-f0a2-446d-951d-b0ce87696531)

