LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE    := ApkPatchDemo
LOCAL_CXXFLAGS :=
LOCAL_C_INCLUDES := $(LOCAL_PATH)
LOCAL_SRC_FILES := com_wedcel_utils_PatchUtils.c
LOCAL_LDLIBS := -lz -llog
include $(BUILD_SHARED_LIBRARY)

