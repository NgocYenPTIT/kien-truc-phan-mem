@echo off
REM Script để chạy các microservices trong một cửa sổ CMD duy nhất

echo Dang bat dau tat ca microservices...

REM Tạo và chuyển đến một thư mục tạm thời để lưu trữ các file log
if not exist "D:\IDE\kien-truc-phan-mem-all-in-one\logs" mkdir "D:\IDE\kien-truc-phan-mem-all-in-one\logs"

REM Khởi động user-service trong background và redirect output
echo Dang khoi dong user-service...
start /b cmd /c "cd /d D:\IDE\kien-truc-phan-mem-all-in-one\user-service && gradlew bootRun > D:\IDE\kien-truc-phan-mem-all-in-one\logs\user-service.log 2>&1"

REM Chờ một chút trước khi khởi động service tiếp theo

REM Khởi động auth-service trong background và redirect output
echo Dang khoi dong auth-service...
start /b cmd /c "cd /d D:\IDE\kien-truc-phan-mem-all-in-one\auth-service && gradlew bootRun > D:\IDE\kien-truc-phan-mem-all-in-one\logs\auth-service.log 2>&1"

REM Khởi động tournament-service trong background và redirect output
echo Dang khoi dong tournament-service...
start /b cmd /c "cd /d D:\IDE\kien-truc-phan-mem-all-in-one\tournament-service && gradlew bootRun > D:\IDE\kien-truc-phan-mem-all-in-one\logs\tournament-service.log 2>&1"

echo Tat ca microservices da duoc khoi dong!
echo Log files duoc luu tai D:\IDE\kien-truc-phan-mem-all-in-one\logs\

REM Giữ cửa sổ CMD mở
echo.
echo Nhan phim bat ky de dong cua so nay...
pause > nul