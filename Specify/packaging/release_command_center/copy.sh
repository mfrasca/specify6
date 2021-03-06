#!/bin/sh


userAserver="nhm\\johnd@specify6-prod.nhm.ku.edu"

if [ "$1" = "--help" -o "$#" = "0" ] ; then
	echo "[version number] [p|e|m] (user@server)"
	echo " "
	echo "The default (user@sever) value is $userAserver"
	exit
fi

if [ "$2" = "e" ] ; then
	append="_EZDB"
	uappend="-embed"
	v_dmg="dmg"
	v_sh="sh"
	v_exe="exe"
elif [ "$2" = "m" ] ; then 
	append="_Mobile"
	uappend="-mobile"
	v_dmg="tgz"
	v_sh="tar.gz"
	v_exe="zip"
	echo "This script can't handle the Mobile version.  Use scp to put the .zip to the internal server instead."
	exit
else
	append=""
	uappend=""
	v_dmg="dmg"
	v_sh="sh"
	v_exe="exe"
fi

tar -cvf file$uappend.tar  Specify_macos_$1_Beta$append.$v_dmg  Specify_unix_$1_Beta$append.$v_sh  Specify_windows_$1_Beta$append.$v_exe Specify_macos_$1_Beta_update$append.$v_dmg Specify_unix_$1_Beta_update$append.$v_sh Specify_windows_$1_Beta_update$append.$v_exe  updates$uappend.xml

if [ "${#3}" -gt "2" ] ; then
	userAserver="$3"
fi

scp file$uappend.tar  $userAserver:.

rm file.tar
