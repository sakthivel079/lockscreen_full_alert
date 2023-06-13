import 'package:flutter/material.dart';
import 'dart:async';

import 'package:lockscreen_notification/lockscreen_notification.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  @override
  void initState() {
    super.initState();
  }
  

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: InkWell(
            onTap: (){
              debugPrint("clicked");
              Future.delayed(const Duration(seconds: 10),(){
                  LockscreenNotification().showDialog(
                      "{data: {\"mode\":\"cash\",\"redirect\":\"transactions\",\"amount\":\"10.00\",\"trans_id\":\"3435142\",\"message\":\"You received \\u20B910.00. Reference number 3435142. Payment collected from MdBalaji\",\"title\":\"Transaction Successful\"}, click_action: FLUTTER_NOTIFICATION_CLICK}");
              });
              
            },
            child: Text('Running on:')),
        ),
      ),
    );
  }
}
