fun main(args: Array<String>) {

    val spreadsheet = "62\t1649\t1731\t76\t51\t1295\t349\t719\t52\t1984\t2015\t2171\t981\t1809\t181\t1715\n" +
            "161\t99\t1506\t1658\t84\t78\t533\t242\t1685\t86\t107\t1548\t670\t960\t1641\t610\n" +
            "95\t2420\t2404\t2293\t542\t2107\t2198\t121\t109\t209\t2759\t1373\t1446\t905\t1837\t111\n" +
            "552\t186\t751\t527\t696\t164\t114\t530\t558\t307\t252\t200\t481\t142\t205\t479\n" +
            "581\t1344\t994\t1413\t120\t112\t656\t1315\t1249\t193\t1411\t1280\t110\t103\t74\t1007\n" +
            "2536\t5252\t159\t179\t4701\t1264\t1400\t2313\t4237\t161\t142\t4336\t1061\t3987\t2268\t4669\n" +
            "3270\t1026\t381\t185\t293\t3520\t1705\t1610\t3302\t628\t3420\t524\t3172\t244\t295\t39\n" +
            "4142\t1835\t4137\t3821\t3730\t2094\t468\t141\t150\t3982\t147\t4271\t1741\t2039\t4410\t179\n" +
            "1796\t83\t2039\t1252\t84\t1641\t2165\t1218\t1936\t335\t1807\t2268\t66\t102\t1977\t2445\n" +
            "96\t65\t201\t275\t257\t282\t233\t60\t57\t200\t216\t134\t72\t105\t81\t212\n" +
            "3218\t5576\t5616\t5253\t178\t3317\t6147\t5973\t2424\t274\t4878\t234\t200\t4781\t5372\t276\n" +
            "4171\t2436\t134\t3705\t3831\t3952\t2603\t115\t660\t125\t610\t152\t4517\t587\t1554\t619\n" +
            "2970\t128\t2877\t1565\t1001\t167\t254\t2672\t59\t473\t2086\t181\t1305\t162\t1663\t2918\n" +
            "271\t348\t229\t278\t981\t1785\t2290\t516\t473\t2037\t737\t2291\t2521\t1494\t1121\t244\n" +
            "2208\t2236\t1451\t621\t1937\t1952\t865\t61\t1934\t49\t1510\t50\t1767\t59\t194\t1344\n" +
            "94\t2312\t2397\t333\t1192\t106\t2713\t2351\t2650\t2663\t703\t157\t89\t510\t1824\t125"

    val checksum = spreadsheet.split("\n")
            .map { line ->
                val highest = line.split("\t").map { it.toInt() }.max()
                val lowest = line.split("\t").map { it.toInt() }.min()

                highest!!.minus(lowest!!)
            }
            .sum()

    println(checksum)

    val evenlyDivisibleSum = spreadsheet.split("\n")
            .mapIndexed { index, line ->
                val el = line.split("\t")

                el.map { it.toInt() }
                        .flatMap { n1 -> el.map { it.toInt() }.map { n2 -> if (n1 > n2) Pair(n1, n2) else Pair(n2, n1) } }
                        .filter { p -> p.first != p.second }
                        .filter { p -> p.first % p.second == 0 }
                        .first()
            }
            .map { p -> p.first / p.second }
            .sum()

    println(evenlyDivisibleSum)

}

