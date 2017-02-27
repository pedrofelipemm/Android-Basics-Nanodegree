for d in */gradlew ; do
    cd ./$(dirname $d)
    gradle clean
    echo Cleaning gradle files..
    rm -R ./.gradle
   echo Cleaning Android Studio files
    rm -R ./.idea
    cd ../
done
