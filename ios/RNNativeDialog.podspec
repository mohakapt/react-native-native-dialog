
Pod::Spec.new do |s|
  s.name         = "RNNativeDialog"
  s.version      = "1.0.0"
  s.summary      = "RNNativeDialog"
  s.description  = <<-DESC
                  RNNativeDialog
                   DESC
  s.homepage     = ""
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "author@domain.cn" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/author/RNNativeDialog.git", :tag => "master" }
  s.source_files  = "RNNativeDialog/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  #s.dependency "others"

end

  