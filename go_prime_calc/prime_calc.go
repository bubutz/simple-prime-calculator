package main

// BUG: fix the fail when command prompt argv 1 is empty
//      fix argv1 <= 0 is invalid
//      fix 

import (
    "fmt"
    "strconv"
    "os"
    "time"
    //"math"
)

func validate_is_prime(num int, prime_list[] int) bool {
    upperbound := num / 2
    i := 0
    for prime_list[i] <= upperbound {
        if num % prime_list[i] == 0 {
            return false
        }
        i += 1
    }
    return true
}


func main() {
    
    x, err := strconv.Atoi(os.Args[1])
    if err != nil { x = 100000 }

    prime_list := []int{2}
    i := 3
    start_time := time.Now()
    for i <= x {
        if validate_is_prime(i, prime_list) {
            prime_list = append(prime_list, i)
        }
        i += 1
    }
    end_time := time.Now()
    elapsed_time := end_time.Sub(start_time)
    fmt.Println("Calculated total of ", x)
    fmt.Println("  Time=", elapsed_time, "Seconds")
    fmt.Println("  x=", x)
    fmt.Println("  Total prime=", len(prime_list))
    //fmt.Println("  First prime=", prime_list[0])
    fmt.Println("  Last prime=", prime_list[len(prime_list)-1])


    prime_list = nil

    prime_list = append(prime_list, 2)
    i = 3
    start_time = time.Now()
    for len(prime_list) < x {
        if validate_is_prime(i, prime_list) {
            prime_list = append(prime_list, i)
        }
        i += 1
    }
    end_time = time.Now()
    elapsed_time = end_time.Sub(start_time)
    fmt.Println("Calculated up to ", x)
    fmt.Println("  Time=", elapsed_time, "Seconds")
    fmt.Println("  x=", x)
    fmt.Println("  Total prime=", len(prime_list))
    //fmt.Println("  First prime=", prime_list[0])
    fmt.Println("  Last prime=", prime_list[len(prime_list)-1])
}

