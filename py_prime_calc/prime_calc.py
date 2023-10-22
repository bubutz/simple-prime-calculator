#!/usr/bin/python3

import sys, time

def validate_is_prime(num, prime_list):
    # We only need to calculate up to 1/2 of the number
    # We can skip all the compount number and only use the prime as divider

    upperbound = num // 2
    i = 0
    while prime_list[i] <= upperbound:
        if num % prime_list[i] == 0:
            return False
        i += 1

    return True


def main():
    try:
        x = int(sys.argv[1])
    except (IndexError, ValueError) as error:
        x = 100000


    print("Calculate total of ", x, " elements.")
    prime_list = [2] # Using first prime '2' to initialize the list
    i = 3
    start_time = time.time()
    while i <= x:
        if validate_is_prime(i, prime_list):
            prime_list.append(i)
        i += 1
    elapsed_time = time.time() - start_time
    #print("Time=", elapsed_time, "x=", x, "Total prime=", len(prime_list), "Last prime=", prime_list[-1])
    print("  Time=", elapsed_time, "Seconds")
    print("  x=", x, "")
    print("  Total prime=", len(prime_list))
    print("  First prime=", prime_list[0])
    print("  Last prime=", prime_list[-1])

    prime_list.clear()


    print("Calculate up to ", x, " primes.")
    prime_list = [2]
    i = 3
    start_time = time.time()
    while len(prime_list) < x:
        if validate_is_prime(i, prime_list):
            prime_list.append(i)
        i += 1
    elapsed_time = time.time() - start_time
    #print("Time=", elapsed_time, "x=", x, "Total prime=", len(prime_list), "Last prime=", prime_list[-1])
    print("  Time=", elapsed_time, "Seconds")
    print("  x=", x, "")
    print("  Total prime=", len(prime_list))
    print("  First prime=", prime_list[0])
    print("  Last prime=", prime_list[-1])



if __name__ == "__main__":
    main()
