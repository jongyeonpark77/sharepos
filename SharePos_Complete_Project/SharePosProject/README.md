# SharePos - 위치 공유 앱

SharePos는 구글 맵을 활용한 현대적인 위치 공유 Android 앱입니다.

## 🚀 주요 기능

- **구글 맵 통합**: 실시간 지도 표시
- **현재 위치 표시**: GPS 기반 정확한 위치 확인
- **위치 공유**: 다른 앱으로 위치 정보 공유
- **최신 Android 14 지원**: 최신 안드로이드 버전 완벽 지원
- **Material Design 3**: 현대적이고 아름다운 UI

## 📱 설치 방법

### 방법 1: GitHub Actions 빌드 (권장)

1. 이 저장소를 GitHub에 업로드
2. GitHub Actions 탭에서 빌드 진행 상황 확인
3. 빌드 완료 후 "Artifacts"에서 APK 다운로드
4. 휴대폰에서 APK 설치

### 방법 2: 직접 빌드

```bash
# 프로젝트 클론
git clone [repository-url]
cd SharePosProject

# 빌드 실행
./gradlew assembleRelease
```

## 🔧 기술 스택

- **Android SDK**: 34 (Android 14)
- **Gradle**: 8.2.2
- **Google Maps**: 18.2.0
- **Google Location Services**: 21.1.0
- **Material Design**: 1.11.0
- **AndroidX**: 최신 버전

## 📋 권한 요구사항

- `INTERNET`: 구글 맵 로딩
- `ACCESS_FINE_LOCATION`: 정확한 위치 정보
- `ACCESS_COARSE_LOCATION`: 대략적인 위치 정보
- `ACCESS_NETWORK_STATE`: 네트워크 상태 확인

## 🎯 사용 방법

1. 앱 실행
2. 위치 권한 허용
3. "현재 위치" 버튼으로 GPS 위치 확인
4. "위치 공유" 버튼으로 다른 앱에 위치 공유

## 🔑 API 키 설정

구글 맵 API 키가 이미 설정되어 있습니다:
- API 키: `AIzaSyAfJ3rl7eabBmi7gyjkmhoMRRd3HM45pnY`

## 📦 빌드 결과물

- **Debug APK**: `app/build/outputs/apk/debug/app-debug.apk`
- **Release APK**: `app/build/outputs/apk/release/app-release.apk`

## 🤝 기여하기

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다.

## 📞 문의

프로젝트에 대한 문의사항이 있으시면 이슈를 생성해주세요. 