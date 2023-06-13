

import 'package:flutter/services.dart';

class LockscreenNotification {
  final methodChannel  = const MethodChannel("lockscreen_notification");
  
  Future<void> showDialog(String content) async {
    methodChannel.invokeMethod("dialog", {"content": content});
  }

  Future<void> initBackgroundService() async {
    methodChannel.invokeMethod("start", {"start": "true"});
  }
}
