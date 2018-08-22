common = load 'ci/common.groovy'

def uploadArtifact() {
  def artifact_dir = pwd() + '/android/app/build/outputs/apk/release/'
  println (artifact_dir + 'app-release.apk')
  def artifact = (artifact_dir + 'app-release.apk')
  def server = Artifactory.server('artifacts')
  def filename = "im.status.ethereum-${GIT_COMMIT.take(6)}-n-fl.apk"
  def newArtifact = (artifact_dir + filename)
  sh "cp ${artifact} ${newArtifact}"
  def uploadSpec = '{ "files": [ { "pattern": "*apk/release/' + filename + '", "target": "nightlies-local" }]}'
  def buildInfo = server.upload(uploadSpec)
  return 'http://artifacts.status.im:8081/artifactory/nightlies-local/' + filename
}

def compile(type = 'nightly') {
  common.tagBuild()
  def gradleOpt = ''
  if (type == 'release') {
    gradleOpt = "-PreleaseVersion=${common.version()}"
  }
  dir('android') {
    sh './gradlew react-native-android:installArchives'
    sh "./gradlew assembleRelease ${gradleOpt}"
  }
  def pkg = "StatusIm-${GIT_COMMIT.take(6)}${(type == 'e2e' ? '-e2e' : '')}.apk"
  sh "cp android/app/build/outputs/apk/release/app-release.apk ${pkg}"
  return pkg
}

def uploadToPlayStore() {
  withCredentials([
    string(credentialsId: "SUPPLY_JSON_KEY_DATA", variable: 'GOOGLE_PLAY_JSON_KEY'),
    string(credentialsId: "SLACK_URL", variable: 'SLACK_URL')
  ]) {
    sh 'bundle exec fastlane android nightly'
  }
}

def uploadToSauceLabs() {
  env.SAUCE_LABS_APK = "im.status.ethereum-e2e-${GIT_COMMIT.take(6)}.apk"
  withCredentials([
    string(credentialsId: 'SAUCE_ACCESS_KEY', variable: 'SAUCE_ACCESS_KEY'),
    string(credentialsId: 'SAUCE_USERNAME', variable: 'SAUCE_USERNAME'),
    string(credentialsId: 'GIT_HUB_TOKEN', variable: 'GITHUB_TOKEN'),
    string(credentialsId: 'SLACK_JENKINS_WEBHOOK', variable: 'SLACK_URL')
  ]) {
    sh 'fastlane android saucelabs'
  }
  return env.SAUCE_LABS_APK
}

def uploadToDiawi() {
  env.SAUCE_LABS_APK = "im.status.ethereum-e2e-${GIT_COMMIT.take(6)}.apk"
  withCredentials([
    string(credentialsId: 'SAUCE_ACCESS_KEY', variable: 'SAUCE_ACCESS_KEY'),
    string(credentialsId: 'SAUCE_USERNAME', variable: 'SAUCE_USERNAME'),
    string(credentialsId: 'GIT_HUB_TOKEN', variable: 'GITHUB_TOKEN'),
    string(credentialsId: 'SLACK_JENKINS_WEBHOOK', variable: 'SLACK_URL')
  ]) {
    sh 'fastlane android saucelabs'
  }
  return env.SAUCE_LABS_APK
}

return this
