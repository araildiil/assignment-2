import csv
import os
import matplotlib.pyplot as plt

TABLES_DIR = "results/tables"
PLOTS_DIR = "results/plots"

os.makedirs(PLOTS_DIR, exist_ok=True)


def read_csv(filename):
    path = os.path.join(TABLES_DIR, filename)
    with open(path, newline="") as f:
        reader = csv.DictReader(f)
        return list(reader)


def plot_workload1():
    rows = read_csv("workload1_random_access.csv")
    ns = sorted(set(int(r["n"]) for r in rows))

    array_times = [int(r["avgTimeNs"]) for r in rows if r["structure"] == "DynamicArray"]
    list_times = [int(r["avgTimeNs"]) for r in rows if r["structure"] == "LinkedList"]

    plt.figure()
    plt.plot(ns, array_times, marker="o", label="DynamicArray")
    plt.plot(ns, list_times, marker="o", label="LinkedList")
    plt.xscale("log")
    plt.yscale("log")
    plt.xlabel("n")
    plt.ylabel("Average execution time (ns)")
    plt.title("Workload 1 — Random Access: Execution Time vs n")
    plt.legend()
    plt.grid(True)
    plt.savefig(os.path.join(PLOTS_DIR, "workload1_time.png"))
    plt.close()


def plot_workload2():
    rows = read_csv("workload2_search.csv")
    ns = sorted(set(int(r["n"]) for r in rows))

    array_times = [int(r["avgTimeNs"]) for r in rows if r["structure"] == "DynamicArray"]
    list_times = [int(r["avgTimeNs"]) for r in rows if r["structure"] == "LinkedList"]
    array_comparisons = [int(r["comparisons"]) for r in rows if r["structure"] == "DynamicArray"]
    list_comparisons = [int(r["comparisons"]) for r in rows if r["structure"] == "LinkedList"]

    plt.figure()
    plt.plot(ns, array_times, marker="o", label="DynamicArray")
    plt.plot(ns, list_times, marker="o", label="LinkedList")
    plt.xscale("log")
    plt.xlabel("n")
    plt.ylabel("Average execution time (ns)")
    plt.title("Workload 2 — Search: Execution Time vs n")
    plt.legend()
    plt.grid(True)
    plt.savefig(os.path.join(PLOTS_DIR, "workload2_time.png"))
    plt.close()

    plt.figure()
    plt.plot(ns, array_comparisons, marker="o", label="DynamicArray")
    plt.plot(ns, list_comparisons, marker="o", label="LinkedList")
    plt.xscale("log")
    plt.xlabel("n")
    plt.ylabel("Comparisons")
    plt.title("Workload 2 — Search: Comparisons vs n")
    plt.legend()
    plt.grid(True)
    plt.savefig(os.path.join(PLOTS_DIR, "workload2_comparisons.png"))
    plt.close()


def plot_workload3():
    rows = read_csv("workload3_insert_remove.csv")
    ns = sorted(set(int(r["n"]) for r in rows))

    def series(structure, operation, position):
        return [
            int(r["avgTimeNs"]) for n in ns
            for r in rows
            if r["structure"] == structure
            and r["operation"] == operation
            and r["position"] == position
            and int(r["n"]) == n
        ]

    for operation in ["insert", "remove"]:
        plt.figure()
        for structure in ["DynamicArray", "LinkedList"]:
            for position in ["beginning", "middle"]:
                values = series(structure, operation, position)
                plt.plot(ns, values, marker="o", label=f"{structure} ({position})")
        plt.xscale("log")
        plt.xlabel("n")
        plt.ylabel("Average execution time (ns)")
        plt.title(f"Workload 3 — {operation.capitalize()}: Execution Time vs n")
        plt.legend()
        plt.grid(True)
        plt.savefig(os.path.join(PLOTS_DIR, f"workload3_{operation}_time.png"))
        plt.close()

    plt.figure()
    for structure in ["DynamicArray", "LinkedList"]:
        values = series(structure, "insert", "beginning")
        plt.plot(ns, values, marker="o", label=f"{structure} movements")
    plt.xscale("log")
    plt.xlabel("n")
    plt.ylabel("Movements")
    plt.title("Workload 3 — Insert (beginning): Movements vs n")
    plt.legend()
    plt.grid(True)
    plt.savefig(os.path.join(PLOTS_DIR, "workload3_movements.png"))
    plt.close()


def plot_workload4():
    rows = read_csv("workload4_heap.csv")
    ns = [int(r["n"]) for r in rows]
    insert_times = [int(r["insertTimeNs"]) for r in rows]
    extract_times = [int(r["extractTimeNs"]) for r in rows]
    comparisons = [int(r["comparisons"]) for r in rows]

    plt.figure()
    plt.plot(ns, insert_times, marker="o", label="insert (total)")
    plt.plot(ns, extract_times, marker="o", label="extractMin (total)")
    plt.xscale("log")
    plt.yscale("log")
    plt.xlabel("n")
    plt.ylabel("Total execution time (ns)")
    plt.title("Workload 4 — Min-Heap: Execution Time vs n")
    plt.legend()
    plt.grid(True)
    plt.savefig(os.path.join(PLOTS_DIR, "workload4_time.png"))
    plt.close()

    plt.figure()
    plt.plot(ns, comparisons, marker="o", label="comparisons")
    plt.xscale("log")
    plt.xlabel("n")
    plt.ylabel("Comparisons")
    plt.title("Workload 4 — Min-Heap: Comparisons vs n")
    plt.legend()
    plt.grid(True)
    plt.savefig(os.path.join(PLOTS_DIR, "workload4_comparisons.png"))
    plt.close()


if __name__ == "__main__":
    plot_workload1()
    plot_workload2()
    plot_workload3()
    plot_workload4()
    print("All plots saved to", PLOTS_DIR)