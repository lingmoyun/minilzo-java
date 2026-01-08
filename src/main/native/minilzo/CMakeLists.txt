cmake_minimum_required(VERSION 3.6)
project(minilzo_java)

set(CMAKE_CXX_STANDARD 14)

# -------- macOS Universal Binary --------
if (APPLE)
    message(STATUS "Building macOS Universal Binary (arm64 + x86_64)")

    # 必须在 add_library 之前设置
    set(CMAKE_OSX_ARCHITECTURES "arm64;x86_64" CACHE STRING "" FORCE)
endif ()

file(GLOB CORE_INCLUDE_DIRS ${CMAKE_CURRENT_SOURCE_DIR}/inc)
file(GLOB_RECURSE CORE_SOURCE_FILES ${CMAKE_CURRENT_SOURCE_DIR}/src/*.c)

message(STATUS "System: ${CMAKE_SYSTEM_NAME}")

# -------- JNI include --------
if (WIN32)
    set(JAVA_PLATFORM_INCLUDE win32)
elseif (APPLE)
    set(JAVA_PLATFORM_INCLUDE darwin)
elseif (UNIX)
    set(JAVA_PLATFORM_INCLUDE linux)
else ()
    message(FATAL_ERROR "Unsupported platform")
endif ()

include_directories(
        $ENV{JAVA_HOME}/include
        $ENV{JAVA_HOME}/include/${JAVA_PLATFORM_INCLUDE}
        ${CORE_INCLUDE_DIRS}
)

add_library(${PROJECT_NAME} SHARED ${CORE_SOURCE_FILES})

target_link_libraries(${PROJECT_NAME})