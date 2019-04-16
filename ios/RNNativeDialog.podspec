require 'json'
package = JSON.parse(File.read(File.join(__dir__, '../', 'package.json')))

Pod::Spec.new do |s|
  s.name          = 'RNNativeDialog'
  s.version       = package['version']
  s.summary       = package['description']
  
  s.author        = { 'Heysem KATBY' => 'mohakapt@gmail.com' }
  s.homepage      = 'https://github.com/mohakapt/react-native-native-dialog'
  s.license       = package['license']
  s.platform      = :ios, "9.0"
  s.swift_version = '4.2'
  
  s.source        = { :git => "https://github.com/mohakapt/react-native-native-dialog.git", :tag => s.version.to_s }
  s.source_files  = 'RNNativeDialog/**/*.{h,m,swift}'
  s.resources = "RNNativeDialog/**/*.{xib}"

  s.dependency "React"
  s.dependency "PopupDialog"
end
