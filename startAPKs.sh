
#
# author Huber Flores
#

#echo 600$i
# 6001, 6002, 6003...

for i in {1..9}
do
 nohup ./rund.sh -cp g_chess_Server__600${i}.apk edu.ut.mobile.network.Main &
done


for j in {10..20}
do
 nohup ./rund.sh -cp g_chess_Server__60${j}.apk edu.ut.mobile.network.Main &
done

netstat -ntlp | grep :60

netstat -ntlp | grep :601

netstat -ntlp | grep :602



