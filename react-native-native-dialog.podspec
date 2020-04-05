require "json"
package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-native-dialog"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  react-native-native-dialog
                   DESC
  s.authors      = { "Heysem Katibi" => "mohakapt@gmail.com" }
  s.homepage     = "https://github.com/mohakapt/react-native-native-dialog"
  s.license    = { :type => "MIT", :file => "LICENSE" }
  s.platform     = :ios, "9.0"
  s.swift_version = '4.2'
  s.source       = { :git => "https://github.com/mohakapt/react-native-native-dialog.git", :tag => "#{s.version}" }
  s.requires_arc = true

  s.source_files = "ios/**/*.{h,m,swift}"
  s.resources = "ios/**/*.{xib}"

  s.dependency "React"
  s.dependency "PopupDialog"
  s.dependency "Cosmos"
  s.dependency "RatingStar"
end

