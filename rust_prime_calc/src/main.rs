use std::time::Instant;

fn main() {
    // println!("Prime calculator");

    // -----Read from input and convert to int
    // let mut prime_range = String::new();
    // io::stdin()
    //     .read_line(&mut prime_range)
    //     .expect("Failed to read line");
    // let prime_range: u64 = prime_range
    //                         .trim()
    //                         .parse()
    //                         .expect("Please specify a positive number");

    // ----fixed range
    let prime_range: u64 = 1_000_000;

    // ----Used to test HashMap, it's not inserted in order so.... used Vector instead
    // let mut prime_lists: HashMap<u64, bool> = HashMap::from([(2, true)]);
    // let mut n = 3;
    // while n <= prime_range {
    //     prime_lists.insert(n, false);
    //     n += 1;
    // }
    let mut prime_lists: Vec<(u64, bool)> = vec![(2, true)];
    let mut i: u64 = 3;
    
    let prime_start_time = Instant::now();
    while i <= prime_range {
        // if calc_prime(i) == true {
        //     prime_lists.push((i, true));
        // }
        // match calc_prime(i) {
        //     true => prime_lists.push((i, true)),
        //     false => prime_lists.push((i, false))
        // }

        // prime_lists.push((i, calc_prime(i)));

        prime_lists.push((i, calc_prime_v2(i, &prime_lists)));
        i += 1;
    }
    let prime_end_time = prime_start_time.elapsed();
    println!("Elapsed: {:.2?}", prime_end_time);
    
    // for (primenumber, isprime) in &prime_lists {
    //     if isprime == &true {
    //         println!("{primenumber}:\t{isprime}");
    // }
    // println!("{primenumber}:\t{isprime}");
}

// fn calc_prime(num: u64) -> bool {
//     let range_to_calc = num / 2;
//     for i in 2..=range_to_calc {
//         if num % i == 0 {
//             return false;
//         }
//     }
//     true
// }

fn calc_prime_v2(num: u64, prime_factors: &[(u64, bool)]) -> bool {
    // TODO: Now it's using all that's in the prime list, 
    //       Next is to use upto half of the numbers
    // let range_calc: u64 = num / 2;
 
    let calc_range = num / 2;

    for (primenum, isprime) in prime_factors {
        match isprime {
            false => (),
            true  => {
                if num % primenum == 0 {
                    // println!("{num} is not prime");
                    return false;
                }
            },
        }
        if primenum > &calc_range {
            println!("{num} is prime");
            return true
        }
    }
    println!("{num} is prime");
    true
}

