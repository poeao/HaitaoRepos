down() {
for i in `seq 1 25`
 do
     spacenumber=`expr 20 + $i`
     for j in `seq 1 $spacenumber`
        do
          echo -n ' '
        done

     starnumber=`expr 50 - 2 \* $i - 1`
     for k in `seq 1 $starnumber`
     do
          echo -n '*'
     done

     echo
done
}
up() {
for i in `seq 1 25`
 do
     spacenumber=`expr 40 - $i`
     for j in `seq 1 $spacenumber`
        do
          echo -n ' '
        done

     starnumber=`expr 2 \* $i - 1`
     for k in `seq 1 $starnumber`
     do
          echo -n '*'
     done

     echo
done
}
case "$1" in
  -u)
       up
        ;;
  -d)
       down
        ;;
  *)
        echo $"Usage: $0 {-u|-d}"
        exit 2
esac
