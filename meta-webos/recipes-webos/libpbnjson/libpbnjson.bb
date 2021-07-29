# Copyright (c) 2012-2022 LG Electronics, Inc.

SUMMARY = "Palm's Better Native JSON library"
AUTHOR = "Anatolii Sakhnik <anatolii.sakhnik@lge.com>"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "yajl glib-2.0 gperf-native flex-native lemon-native gmp uriparser boost"

WEBOS_VERSION = "2.15.0-4_bcd35ead52db4153cef68bb6e4ef0f7fc7f51510"
PR = "r9"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library

PACKAGECONFIG ??= ""
PACKAGECONFIG:append:class-native = " tools"

# These are the defaults, but explicitly specify so that readers know they exist
EXTRA_OECMAKE += "-DWITH_DOCS:BOOL=FALSE -DWITH_TESTS:BOOL=FALSE -DNO_LOGGING:BOOL=TRUE"
# Disable #line statements in autogenerated files
EXTRA_OECMAKE += "-DLEMON_WITH_LINE:BOOL=FALSE"
# Don't use CMake detection mechanisms for AR (it fails for class-native)
EXTRA_OECMAKE += "-DCMAKE_AR:FILEPATH=${AR}"

PACKAGECONFIG[tools] = "-DPBNJSON_INSTALL_TOOLS:BOOL=TRUE,-DPBNJSON_INSTALL_TOOLS:BOOL=FALSE"

# Otherwise it fails with:
# libpbnjson-native/2.15.0-68-r7/git/src/pbnjson_c/validation/schema_builder.c:24:10: fatal error: schema_keywords.h: No such file or directory
OECMAKE_GENERATOR="Unix Makefiles"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

BBCLASSEXTEND = "native"

SRC_URI += "file://0001-pbnjson.h-don-t-include-all-c-header-in-extern-C.patch"
