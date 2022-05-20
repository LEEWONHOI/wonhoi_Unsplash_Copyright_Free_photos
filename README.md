# Unsplash Copyright Free Photos


## 💻 Use Stack 
<img alt="Android" src ="https://img.shields.io/badge/Android-3DDC84.svg?&style=for-the-badge&logo=Android&logoColor=white"/> <img alt="Android" src ="https://img.shields.io/badge/Kotlin-7F52FF.svg?&style=for-the-badge&logo=Kotlin&logoColor=white"/>

<br/>

## 📖 상세 내용

<div align="center">
  <img src="https://user-images.githubusercontent.com/78194843/169426882-fd762ce7-e29c-4358-b10f-8d7afc595311.png" width="30%" >
  
  <img src="https://user-images.githubusercontent.com/78194843/169426887-56bf9600-c959-448d-88ff-735de136ef9e.png" width="30%" >
  
  <img src="https://user-images.githubusercontent.com/78194843/169426889-bc6eff25-74ee-4de2-a1e4-d04189bfe897.png" width="30%" >
  
  
</div>

<div align="center">
  <img src="https://user-images.githubusercontent.com/78194843/169426891-eb8f4e8d-1930-4788-9d6c-047515e73bfd.png" width="30%" >
  
  <img src="https://user-images.githubusercontent.com/78194843/169426893-a1bdc011-84e4-4de6-8314-51b506636225.png" width="30%" >
  
  <img src="https://user-images.githubusercontent.com/78194843/169426894-92f6c4ac-3ef7-43f3-a3c1-1edaa6862573.png" width="30%" >
    
</div>

<div align="center">
  <img src="https://user-images.githubusercontent.com/78194843/169426896-893739a4-af78-4c6f-a190-0dd89ca3ba0d.gif" width="100%" >
</div>

<br/>

> 🍽️ 해당 프로젝트는 **Fastcampus 의  Android with Kotlin - Upper Intermediate (part 4) - Ch07 저작권 무료 이미지 검색기 강의**를 보며 진행한 프로젝트입니다.

<br/>

<details>
<summary>⛔라이센스 관련 주의사항</summary>
<div markdown="1">

  - ✅ the [Ghost integration brings Unsplash inside Ghost’s editor to improve the workflow of finding beautiful images](https://blog.ghost.org/unsplash/). Without the integration, the app still has a lot of value to its users.
  - ✅ the [Trello integration brings Unsplash images inside Trello's productivity app to allow uses to customize the backgrounds of their boards](https://blog.trello.com/unsplash-photo-board-backgrounds). Without the integration, the app still has a lot of value to its users.
  - ✅ the [Medium integration allows writers to include Unsplash images with their posts](https://unsplash.com/blog/medium-unsplash/). Without the integration, the app still has a lot of value to its users.
  - 🚫 a wallpaper app returns Unsplash images for downloading. Without the integration, the app has no content and no value to users.
  - 🚫 an unofficial Unsplash for Android app allows users to search for Unsplash images and download them. Without the API, the app has no content and no value to users.
    

  >💡 이러한 Unsplash Guideline에 의하여 해당 앱은 교육, 참고용으로만 사용이 가능합니다. 참고해주시기 바랍니다. 자세한 사항은 [ [URL](https://help.unsplash.com/en/articles/2511257-guideline-replicating-unsplash) ]

</div>
</details>

    
<br/>


## 🛠️ 사용 라이브러리

- Coroutines
- Glide
- ShimmerLayout
- Retrofit2
- okhttp3
- WallpaperManager
- App Widgets

<br/>
<br/>


## 📱 구현한 기능

- Unsplash Api 로부터 랜덤 사진들을 가져올 수 있다.
- 검색한 랜덤 사진을 다운 받을 수 있다.
- 다운로드한 사진을 배경화면으로 설정할 수 있다.
- 로딩할 때 Loading Shimmer 를 볼 수 있다.

<br/>
<br/>


## 🙏 요청 API

- Unsplash API           [ [URL](https://unsplash.com/documentation) ]

<br/>
<br/>


## 💡 참고한 문서

- Unsplash API 에서 get a random photo 방식으로 랜덤사진 받아오기   [ [URL](https://unsplash.com/documentation#get-a-random-photo) ]
- SnackBar Action 사용 방법 [ [URL](https://material.io/components/snackbars) ]
- MediaStore 를 사용하여 외부 저장소에 사진 저장하기 구현 [ [URL](https://developer.android.com/reference/android/provider/MediaStore) ]
- 저장소 권한 : 안드로이드 10부터 (api 29) scoped storage 가 기본적으로 적용되지만, 하위 버전에 대해서는 storage-related permissions 요청이 필요함 [ [URL](https://developer.android.com/training/data-storage/shared/media#request-permissions) ]
- 큰 사진 저장할 경우 필요한 IS_PENDING을 사용해서 미디어 파일의 대기 중 상태전환 (안드로이드 10) [ [URL](https://developer.android.com/training/data-storage/shared/media#toggle-pending-status) ]
- Accessing a provider 콘텐츠 프로바이더에 접근하기 (ContentResolver) [ [URL](https://developer.android.com/guide/topics/providers/content-provider-basics#ClientProvider) ]
- WallpaperManager 의 setBitmap를 사용하여 배경화면 설정을 구현하기 [ [URL](https://developer.android.com/reference/android/app/WallpaperManager) ]
- 로딩 중 미리 이미지의 위치와 크기를 표시하기 위한 placeholder [ [URL](https://bumptech.github.io/glide/doc/placeholders) ]
- 이미지를 불러오면서 trasformation 적용하기 (Applying Transformations) [ [URL](https://bumptech.github.io/glide/doc/transformations#built-in-types) ]


<br/>
<br/>

